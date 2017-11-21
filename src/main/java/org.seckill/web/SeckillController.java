package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatkillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping//url:模块/资源/{id}/细分/seckill/list
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:seckill/list";
        }
        Seckill seckill =  seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId/exposer}"
                    ,method = RequestMethod.POST
                    ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillID){
        SeckillResult<Exposer> result ;
        try{
            Exposer exposer =  seckillService.excportSeckillUrl(seckillID);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
                    method = RequestMethod.POST
                    ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId
                                                    ,@PathVariable("md5")String md5
                                                    ,@CookieValue(value = "killPhone",required = false)Long killPhone
                                                   ){
        SeckillResult<SeckillExecution> result;

        if(killPhone==null){
         result = new SeckillResult<SeckillExecution>(false,"未注册");
        }
        try {
            SeckillExecution execution =  seckillService.executeSeckill(seckillId,killPhone,md5);
            result =  new SeckillResult<SeckillExecution>(true,execution);
        }catch (RepeatkillException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false,execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution(seckillId,SeckillStatEnum.END);
            result = new SeckillResult<SeckillExecution>(false,execution);
        } catch (Exception e){
            logger.info(e.getMessage(),e);
            SeckillExecution execution = new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false,execution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
