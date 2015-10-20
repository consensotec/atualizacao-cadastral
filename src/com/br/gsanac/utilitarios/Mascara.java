package com.br.gsanac.utilitarios;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascara implements TextWatcher {
	protected boolean isUpdating;
	protected String mask;
	protected EditText ediTxt;

	private String unmask(String s) {
		 return s.replaceAll("[\\.\\-\\/\\(\\)]", "");
	}

	private String trimMask(String s) {
		return s.replaceAll("[\\.\\-\\/\\(\\)]$", "");
	}

	public Mascara(final String mask, final EditText ediTxt) {
		this.mask = mask;
		this.ediTxt = ediTxt;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	@Override
	public void afterTextChanged(Editable s) {
		String str = unmask(s.toString());
		String mascara = "";

		if (isUpdating) {
			isUpdating = false;
			return;
		}

		int i = 0;
		for (char m : mask.toCharArray()) {
			if (m != '#') {
				mascara += m;
				continue;
			}
			try {
				mascara += str.charAt(i);
			} catch (Exception e) {
				break;
			}
			i++;
		}

		mascara = trimMask(mascara);

		isUpdating = true;
		ediTxt.setText(mascara);
		ediTxt.setSelection(mascara.length());
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
}
