package nanshen.data;

import org.nutz.dao.entity.annotation.Table;

/**
 * GoodsConfirm
 *
 * @author WANG Minghao
 */
@Table("OrderGoods")
public class OrderGoods extends Goods {

    public OrderGoods(long cartId, long count, String discountCode, long discountPrice, long originPrice, long price,
                      String remark, String subTitle, String title, long userId, long skuId) {
        this.cartId = cartId;
        this.count = count;
        this.discountCode = discountCode;
        this.discountPrice = discountPrice;
        this.originPrice = originPrice;
        this.price = price;
        this.remark = remark;
        this.subTitle = subTitle;
        this.title = title;
        this.userId = userId;
        this.skuId = skuId;
        this.isCartGoods = false;
    }

    public OrderGoods() {
    }
}
