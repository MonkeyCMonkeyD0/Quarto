package com.quarto.objects.loading;

import com.quarto.engine.objects.TextObject;

public class LoadingDetails extends TextObject {

	@Override
	public void onInit() {
		setText("");
		getScale().set(2);
		getPosition().set(1.5f, 3.25f);
	}
	
}
