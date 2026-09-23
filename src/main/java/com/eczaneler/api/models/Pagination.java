package com.eczaneler.api.models;

import com.google.gson.annotations.SerializedName;

public class Pagination {
    @SerializedName("total")
    private int total;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("has_more")
    private boolean hasMore;

    @SerializedName("prev_page")
    private Integer prevPage;

    @SerializedName("next_page")
    private Integer nextPage;

    public Pagination() {}

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getPerPage() { return perPage; }
    public void setPerPage(int perPage) { this.perPage = perPage; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public boolean isHasMore() { return hasMore; }
    public void setHasMore(boolean hasMore) { this.hasMore = hasMore; }

    public Integer getPrevPage() { return prevPage; }
    public void setPrevPage(Integer prevPage) { this.prevPage = prevPage; }

    public Integer getNextPage() { return nextPage; }
    public void setNextPage(Integer nextPage) { this.nextPage = nextPage; }
}
