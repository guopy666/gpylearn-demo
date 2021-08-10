package com.gpy.designpatterndemo.ratelimiter;

/**
 * @ClassName ApiLimit
 * @Description
 * @Author guopy
 * @Date 2021/8/4 15:48
 */
public class ApiLimit {
    private static final Integer DEFAULT_TIME_UNIT = 1; // 1s

    private String api;
    private Integer limit;
    private Integer unit = DEFAULT_TIME_UNIT;

    public ApiLimit() {
    }

    public ApiLimit(String api, Integer limit){
        this(api, limit, DEFAULT_TIME_UNIT);
    }


    public ApiLimit(String api, Integer limit, Integer unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }
}
