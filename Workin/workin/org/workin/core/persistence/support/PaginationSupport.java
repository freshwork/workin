package org.workin.core.persistence.support;

import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 * @param <T>
 */
public class PaginationSupport<T> implements Serializable {

	private static final long serialVersionUID = -7315205992759496506L;

	public static final int PAGESIZE = 10;

	private int pageSize = PAGESIZE;

	private Object data;

	private int totalCount;

	private int[] indexes = new int[0];

	private int startIndex = 0;

	public PaginationSupport(Object data, int totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setData(data);
		setStartIndex(0);
	}

	public PaginationSupport(Object data, int totalCount, int startIndex) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setData(data);
		setStartIndex(startIndex);
	}

	public PaginationSupport(Object data, int totalCount, int pageSize, int startIndex) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setData(data);
		setStartIndex(startIndex);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageCount() {

		int totalPage = totalCount / pageSize;
		totalPage += totalCount % pageSize == 0 ? 0 : 1;

		return totalPage;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;

			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new int[count];

			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0)
			this.startIndex = 0;
		else if (startIndex >= totalCount)
			this.startIndex = indexes[indexes.length - 1];
		else if (startIndex <= 0)
			this.startIndex = 0;
		else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;

		if (nextIndex >= totalCount)
			return getStartIndex();
		else
			return nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;

		if (previousIndex <= 1)
			return -1;
		else
			return previousIndex;
	}

	public int getLastPageIndex() {
		return (getPageCount() - 1) * pageSize;
	}

	public int getCurrentPageIndex() {
		return getStartIndex() / pageSize + 1;
	}

}
