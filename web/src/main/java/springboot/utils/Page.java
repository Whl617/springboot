package springboot.utils;

public class Page {
    //ÿҳ��ʾ����
	private int everyPage;
	//�ܼ�¼��
	private int totalCount;
	//��ҳ��
	private int totalPage;
	//��ǰҳ
	private int currentPage;
	//��ʼ��
	private int beginIndex;
	//�Ƿ�����һҳ
	private boolean hasNextPage;
	//�Ƿ�����һҳ
	private boolean hasPrePage;
	
	//Ĭ�Ϲ��캯��
	public Page(){}
	//ȫ�����ù��캯��
	public Page(int everyPage, int totalCount, int totalPage, int currentPage, int beginIndex, boolean hasNextPage,
			boolean hasPrePage) {
		this.everyPage = everyPage;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.hasNextPage = hasNextPage;
		this.hasPrePage = hasPrePage;
	}
	
	public int getEveryPage() {
		return everyPage;
	}
	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	
}
