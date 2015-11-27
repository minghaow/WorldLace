package nanshen.data;

/**
 * CombinedLookInfo
 * <br>
 * <strong>Note:</strong> Basic data for lanzhujue.com. Still need to optimize.
 *
 * @author WANG Minghao
 */
public class ComplexLookInfo {

    /** Look Info */
    private LookInfo lookInfo;

    /** Complex Tag info */
    private ComplexTag complexTag;

    public ComplexLookInfo(ComplexTag complexTag, LookInfo lookInfo) {
        this.complexTag = complexTag;
        this.lookInfo = lookInfo;
    }

    public ComplexTag getComplexTag() {
        return complexTag;
    }

    public void setComplexTag(ComplexTag complexTag) {
        this.complexTag = complexTag;
    }

    public LookInfo getLookInfo() {
        return lookInfo;
    }

    public void setLookInfo(LookInfo lookInfo) {
        this.lookInfo = lookInfo;
    }
}
