package com.quarto.engine.engines;

import java.util.Iterator;
import java.util.Map.Entry;

import com.quarto.engine.core.Animation;
import com.quarto.engine.core.Engine;

public class AnimationsEngine extends Engine{
	
	private Iterator<Entry<String, Animation>> it;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getGameEngine().getCurrentScene().getSceneObjects().size(); i++)
			if(this.getGameEngine().getCurrentScene().getSceneObjects().size() > i) {
				it = this.getGameEngine().getCurrentScene().getSceneObjects().get(i).getAnimations().entrySet().iterator();
			    while (it.hasNext())
			        ((Animation) it.next().getValue()).onTick(delta);
			}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

}
