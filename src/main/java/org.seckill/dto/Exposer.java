package org.seckill.dto;

/**
 * 暴露秒杀地址DTO
 */
public class Exposer {
    //是否开启秒杀
    private boolean exposed;
    //一种加密算法
    private String md5;
    //商品id
    private long sId;

    private long now;
    //开始时间
    private long start;
    //结束时间
    private long end;


    public Exposer(boolean exposed, long sId) {
        this.exposed = exposed;
        this.sId = sId;
    }

    public Exposer(boolean exposed, String md5, long sId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.sId = sId;
    }

    public Exposer(boolean exposed, long sId, long now, long start, long end) {
        this.exposed = exposed;
        this.sId = sId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", sId=" + sId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
