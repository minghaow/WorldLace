package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * Volume
 *
 * @author WANG Minghao
 */
@Table("Volume")
public class Volume {

    /** long */
    @Column
    private long a = 0;

    /** short */
    @Column
    private long b = 0;

    /** height */
    @Column
    private long c = 0;

    public Volume() {
    }

    public Volume(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public long getA() {
        return a;
    }

    public void setA(long a) {
        this.a = a;
    }

    public long getB() {
        return b;
    }

    public void setB(long b) {
        this.b = b;
    }

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }
}
