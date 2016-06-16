package nanshen.data.Order;

import nanshen.data.Cart.CartGoods;
import nanshen.data.Goods;
import org.nutz.dao.entity.annotation.Table;

/**
 * GoodsConfirm
 *
 * @author WANG Minghao
 */
@Table("OrderGoods")
public class OrderGoods extends Goods {

    public OrderGoods(long cartId, long count, String discountCode, long discountPrice, long originPrice, long price,
                      String remark, String subTitle, String title, long userId, long skuId, long itemId, String option1,
                      String option2) {
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
        this.itemId = itemId;
        this.option1 = option1;
        this.option2 = option2;
        this.isCartGoods = false;
    }

    public OrderGoods(CartGoods cartGoods, long orderId) {
        this.cartId = cartGoods.getId();
        this.count = cartGoods.getCount();
        this.discountCode = cartGoods.getDiscountCode();
        this.discountPrice = cartGoods.getDiscountPrice();
        this.originPrice = cartGoods.getOriginPrice();
        this.price = cartGoods.getPrice();
        this.remark = cartGoods.getRemark();
        this.subTitle = cartGoods.getSubTitle();
        this.title = cartGoods.getTitle();
        this.userId = cartGoods.getUserId();
        this.skuId = cartGoods.getSkuId();
        this.itemId = cartGoods.getItemId();
        this.option1 = cartGoods.getOption1();
        this.option2 = cartGoods.getOption2();
        this.orderId = orderId;
        this.isCartGoods = false;
    }

    public OrderGoods() {
    }
}
