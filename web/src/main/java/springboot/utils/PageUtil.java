package springboot.utils;
//��Ҷ��Ϣ������
public class PageUtil {
	// ����Page��
	public static Page createPage(int everyPage,int totalCount,int currentPage)
	{
		int totalPage=getTotalPage(everyPage, totalCount);
		int beginIndex=getBeginIndex(everyPage, currentPage);
		boolean hasPrePage=getHasPrePage(currentPage);
		boolean hasNextPage=getHasNextPage(totalPage, currentPage);
		currentPage=getCurrentPage(currentPage,totalPage);
		return new Page(everyPage, totalCount, totalPage, currentPage, beginIndex, hasNextPage, hasPrePage);
	}
	public static Page createPage(Page page,int totalCount)
	{
		int currentPage=getCurrentPage(page.getCurrentPage(),page.getTotalPage());
		int everyPage=getEveryPage(page.getEveryPage());
		int totalPage=getTotalPage(page.getEveryPage(), totalCount);
		int beginIndex=getBeginIndex(page.getEveryPage(), currentPage);
		boolean hasPrePage=getHasPrePage(currentPage);
		boolean hasNextPage=getHasNextPage(totalPage, currentPage);
		return new Page(everyPage, totalCount, totalPage, currentPage, beginIndex, hasNextPage, hasPrePage);
	}
	// ����ÿҳ��ʾ��¼��
	public static int getEveryPage(int everyPage)
	{
		return everyPage == 0 ? 10 : everyPage;
	}
	// ���õ�ǰҳ
	public static int getCurrentPage(int currentPage,int totalPage)
	{
		
		return (currentPage = currentPage <= 0 ? 1 : currentPage) > totalPage?totalPage:currentPage;
	}
	// ������ҳ��
	public static int getTotalPage(int everyPage,int totalCount)
	{
		int totalPage=0;
		if(totalCount%everyPage==0)
			totalPage = totalCount / everyPage ;
		else 
			totalPage=totalCount/everyPage+1;
	    return totalPage;
	}
	//������ʼ��
	public static int getBeginIndex(int everyPage,int currentPage)
	{
		return everyPage*(currentPage-1);
	}
	//�����Ƿ�����һҳ
	public static boolean getHasPrePage(int currentPage)
	{
		return currentPage == 1 ? false : true;
	}
	//�����Ƿ�����һҳ
	public static boolean getHasNextPage(int totalPage,int currentPage)
	{
		return currentPage == totalPage || totalPage==0 ? false : true;
	}
}
 