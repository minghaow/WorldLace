package nanshen.data.Discount;

/**
 * DiscountCalculater
 *
 * @author WANG Minghao
 */
public interface DiscountCalculator {

    /**
     * 通过订单金额计算折扣金额
     *
     * @return
     */
    public long calculateAmount(long total, long discount, long percent, long limit);

}
