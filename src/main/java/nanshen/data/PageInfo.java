package nanshen.data;

import nanshen.constant.SystemConstants;

/**
 * 用于翻页的信息
 *
 * @author WANG Minghao
 */
public class PageInfo {

    /** 页码，从1开始 */
    private int page;

    /**
     * 每页显示的最大数量
     * <p />
     * 由于实现的限制，很多分页方法都不能准确的限定每页内容的数量，只能保证不超过一个最大值
     */
    private int maxNumPerPage;

    public PageInfo(int page) {
        this.page = page;
        this.maxNumPerPage = SystemConstants.DEFAULT_PAGE_SIZE;
    }

    public PageInfo(int page, int maxNumPerPage) {
        this.page = page;
        this.maxNumPerPage = maxNumPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxNumPerPage() {
        return maxNumPerPage;
    }

    public void setMaxNumPerPage(int maxNumPerPage) {
        this.maxNumPerPage = maxNumPerPage;
    }

}
