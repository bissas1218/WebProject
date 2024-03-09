package common;

public class PageNumbering {

	public PageNumberVO pagingProcess(PageNumberVO vo) {
		
		vo.setCurrentPageNum(vo.getPageNum());
		
		int totalPageCnt = vo.getTotalCnt()/vo.getPageListSize();
		if(vo.getTotalCnt()%vo.getPageListSize() > 0) {
			totalPageCnt++;
		}
		vo.setTotalPageCnt(totalPageCnt);
		
		int totalPageBlock = 0;
		if(totalPageCnt % vo.getPageBlockSize() > 0) {
			totalPageBlock = (totalPageCnt / vo.getPageBlockSize()) + 1;
		}else {
			totalPageBlock = totalPageCnt / vo.getPageBlockSize();
		}
		vo.setTotalPageBlock(totalPageBlock);
		
		int currentPageBlock = 0;
		if(vo.getCurrentPageNum() % vo.getPageBlockSize() > 0) {
			currentPageBlock = (vo.getCurrentPageNum() / vo.getPageBlockSize()) + 1;
		}else {
			currentPageBlock = vo.getCurrentPageNum() / vo.getPageBlockSize();
		}
		vo.setCurrentPageBlock(currentPageBlock);
		
		int startPageNum =0;
		int endPageNum = 0;
		startPageNum = (vo.getPageBlockSize() * (currentPageBlock-1))+1;
		endPageNum = vo.getPageBlockSize()*currentPageBlock;
		
		if(endPageNum > totalPageCnt) {
			endPageNum = totalPageCnt;
		}
		vo.setStartPageNum(startPageNum);
		vo.setEndPageNum(endPageNum);
		
		vo.setPageNum((vo.getPageNum()-1) * vo.getPageListSize());
		return vo;
	}
}
