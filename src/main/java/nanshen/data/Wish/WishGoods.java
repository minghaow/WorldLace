package nanshen.data.Wish;

import nanshen.data.Goods;
import org.nutz.dao.entity.annotation.Table;

/**
 * Goods
 *
 * @author WANG Minghao
 */
@Table("WishGoods")
public class WishGoods extends Goods {

    public WishGoods(long cartId, long count, String discountCode, long discountPrice, long originPrice, long price,
                     String remark, String subTitle, String title, long userId, long skuId, long itemId, String option1,
                     String option2, long optionId) {
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
        this.itemId = itemId;
        this.option1 = option1;
        this.option2 = option2;
        this.optionId = optionId;
    }

    public WishGoods() {}

}
