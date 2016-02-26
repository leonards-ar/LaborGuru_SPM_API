/*
 * File name: SessionMenuWrapper.java
 * Creation date: Jun 22, 2009 10:38:32 AM
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.laborguru.model.Menu;
import com.laborguru.model.MenuItem;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SessionMenuWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu menu = null;
	private int selectedItemIndex = -1;
	private int selectedSubItemIndex = -1;

	/**
	 * 
	 */
	public SessionMenuWrapper() {
		super();
	}
	
	/**
	 * 
	 * @param menu
	 */
	public SessionMenuWrapper(Menu menu) {
		this();
		setMenu(menu);
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the selectedItemIndex
	 */
	public int getSelectedItemIndex() {
		return selectedItemIndex;
	}

	/**
	 * @param selectedItemIndex the selectedItemIndex to set
	 */
	public void setSelectedItemIndex(int selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}

	/**
	 * @return the selectedSubItemIndex
	 */
	public int getSelectedSubItemIndex() {
		return selectedSubItemIndex;
	}

	/**
	 * @param selectedSubItemIndex the selectedSubItemIndex to set
	 */
	public void setSelectedSubItemIndex(int selectedSubItemIndex) {
		this.selectedSubItemIndex = selectedSubItemIndex;
	}

	/**
	 * 
	 * @return
	 */
	public List<MenuItem> getItems() {
		return getMenu() != null ? getMenu().getItems() : new ArrayList<MenuItem>();
	}
	
	/**
	 * @return the selectedItem
	 */
	public MenuItem getSelectedItem() {
		if(getItems() != null && getSelectedItemIndex() >= 0 && getSelectedItemIndex() < getItems().size()) {
			return getItems().get(getSelectedItemIndex());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isSelectedItemIndex(int index) {
		return index >=0 && index == getSelectedItemIndex() && index < getItems().size();
	}

	/**
	 * 
	 * @param target1
	 * @param target2
	 * @return
	 */
	private boolean isEqualTarget(String target1, String target2) {
		return target1 != null && target2 != null && removeParamsFromTarget(target1).equals(removeParamsFromTarget(target2));
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	private String removeParamsFromTarget(String target) {
		if(target != null) {
			int start = target.indexOf('?');
			return start < 0 ? target : target.substring(0, start);
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 */
	public void updateSelectedSubItemIndex() {
		MenuItem selected = getSelectedItem();
		if(selected != null && selected.getTarget() != null) {
			List<MenuItem> childs = selected.getOrderedChildMenuItems();
			MenuItem aChild;
			for(int i = 0; i < childs.size(); i++) {
				aChild = childs.get(i);
				if(isEqualTarget(selected.getTarget(), aChild.getTarget())) {
					setSelectedSubItemIndex(i);
				}
			}
		} else {
			setSelectedSubItemIndex(0);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSubMenuItemSelected() {
		return getSelectedSubItemIndex() >= 0;
	}	
	
	
	public void reset() {
		setSelectedItemIndex(0);
		setSelectedSubItemIndex(0);
	}
}
