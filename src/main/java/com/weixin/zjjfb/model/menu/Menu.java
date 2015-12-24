package com.weixin.zjjfb.model.menu;

import java.io.Serializable;

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}

}
