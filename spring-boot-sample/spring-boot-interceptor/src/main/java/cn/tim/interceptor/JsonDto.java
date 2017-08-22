package cn.tim.interceptor;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

/**
 * User: luolibing
 * Date: 2017/8/21 17:59
 */
public class JsonDto {

    private BigDecimal percent;

    @NumberFormat(style = NumberFormat.Style.PERCENT)
    public BigDecimal getPercent() {
        return percent;
    }


    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
