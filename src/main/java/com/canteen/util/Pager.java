package com.canteen.util;


import java.util.List;

public class Pager<T> {

    private int pageSize; // 每页记录数
    private int pageNo; // 当前页
    private int totalPages; // 总页数
    private int totalRecords; // 总记录数
    private List<T> records;

    public int getPageSize() {
        return pageSize;
    }

    // 设置每页记录数
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 10;
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    // 设置当前页
    public void setPageNo(int pageNo) {
        if (pageNo <= 1) {
            this.pageNo = 1;
        } else if (pageNo > this.totalPages) {
            this.pageNo = this.totalPages;
        } else {
            this.pageNo = pageNo;
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    // 设置总页数
    public void setTotalPages() {
        this.totalPages = this.totalRecords % this.pageSize == 0 ? this.totalRecords / this.pageSize
                : this.totalRecords / this.pageSize + 1;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    // 设置总记录数
    public void setTotalRecords(int totalRecords) {
        if (totalRecords >= 0) {
            this.totalRecords = totalRecords;
        } else {
            this.totalRecords = 0;
        }
    }

    public List<T> getList() {
        return records;
    }

    public void setList(List<T> list) {
        this.records = list;
    }

    // 当前页记录列表
    public void queryPager(int pageNo, int pageSize, List<T> list) {
        // 设置总记录数
        if (list != null && list.size() != 0) {
            this.setTotalRecords(list.size());
        } else {
            this.setTotalRecords(0);
        }
        // 设置每页记录数
        this.setPageSize(pageSize);
        // 设置总页数
        this.setTotalPages();
        // 设置当前页
        this.setPageNo(pageNo);
        // 设置当前页记录数
        if (list != null  && list.size() != 0) {
            if (pageNo == this.totalPages) {
                this.records = list.subList((pageNo - 1) * pageSize, this.totalRecords);
            } else {
                this.records = list.subList((pageNo - 1) * pageSize, pageNo * pageSize);
            }
        } else {
            this.records = null;
        }

    }

    public boolean isPrePage() {
        if (this.pageNo > 1) {
            return true;
        }
        return false;
    }

    public boolean isNextPage() {
        if (this.pageNo < this.totalPages) {
            return true;
        }
        return false;
    }

    public int getPrePageNo() {
        if (isPrePage()) {
            return this.pageNo - 1;
        }
        return this.pageNo;
    }

    public int getNextPageNo() {
        if (isNextPage()) {
            return this.pageNo + 1;
        }
        return this.pageNo;
    }

}
