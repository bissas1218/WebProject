package common;

public class PageNumberVO {

	private int pageListSize;
	private int pageBlockSize;
	private int pageNum;
	private int currentPageNum;
	private int totalCnt;
	private int totalPageCnt;
	private int currentPageBlock;
	private int startPageNum;
	private int endPageNum;
	private int totalPageBlock;
	
	public PageNumberVO() {
		
	}
	
	public PageNumberVO(int pageListSize, int pageBlockSize, String pageNum) {
		super();
		this.pageListSize = pageListSize;
		this.pageBlockSize = pageBlockSize;
		if(pageNum == null) {
			this.pageNum = 1; 
		}else {
			this.pageNum = Integer.parseInt(pageNum);
		}
	}
	
	public int getPageListSize() {
		return pageListSize;
	}
	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}
	public int getPageBlockSize() {
		return pageBlockSize;
	}
	public void setPageBlockSize(int pageBlockSize) {
		this.pageBlockSize = pageBlockSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getTotalPageCnt() {
		return totalPageCnt;
	}
	public void setTotalPageCnt(int totalPageCnt) {
		this.totalPageCnt = totalPageCnt;
	}
	public int getCurrentPageBlock() {
		return currentPageBlock;
	}
	public void setCurrentPageBlock(int currentPageBlock) {
		this.currentPageBlock = currentPageBlock;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public int getEndPageNum() {
		return endPageNum;
	}
	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}
	public int getTotalPageBlock() {
		return totalPageBlock;
	}
	public void setTotalPageBlock(int totalPageBlock) {
		this.totalPageBlock = totalPageBlock;
	}
	
}
