package nanshen.data.Sku;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * SkuItemDescription, item
 *
 * @author WANG Minghao
 */
@Table("SkuItemDescription")
public class SkuItemDescription {

    /** ID */
    @Id
    private long id;

    /** item id */
    @Column
    private long itemId;

    /** special points, selling points */
    @Column
    private String points;

    /** size, material.. infos */
    @Column
    private String infos;

    /** main description */
    @Column
    private String description;

    public SkuItemDescription() {
    }

    public SkuItemDescription(String description, String infos, String points, long itemId) {
        this.description = description;
        this.infos = infos;
        this.points = points;
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
