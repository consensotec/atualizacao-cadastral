package com.br.gsanac.map;

import org.mapsforge.map.android.input.TouchEventHandler;
import org.mapsforge.map.android.view.MapView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;

/**
 * Versão personalizada da class {@link MapView} que utiliza a classe
 * {@link CustomTouchGestureDetector}
 *
 * @author André Miranda
 */
public class CustomMapView extends MapView {
	private final TouchEventHandler touchEventHandler;

	public CustomMapView(Context context) {
		super(context);
		ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
		touchEventHandler = new TouchEventHandler(this, viewConfiguration,
				new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener()));

		CustomTouchGestureDetector touchGestureDetector = new CustomTouchGestureDetector(this, viewConfiguration);
		touchEventHandler.addListener(touchGestureDetector);
	}

	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent motionEvent) {
		if (!isClickable()) {
			return false;
		}
		getMapZoomControls().onMapViewTouchEvent(motionEvent);
		return touchEventHandler.onTouchEvent(motionEvent);
	}
}