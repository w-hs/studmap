package de.whs.fia.studmap.collector.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import de.whs.fia.studmap.collector.R;

public class ChoicesDialogFragment extends DialogFragment {
	
	private NFCReaderDialogFragment nfcDialog;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View rootView = inflater.inflate(R.layout.fragment_choicesdialog, null);

		builder.setView(rootView);		
		
		initializeImageButtons(rootView);

		return builder.create();
	}

	private void initializeImageButtons(View rootView) {
		ImageButton nfcBtn = (ImageButton)rootView.findViewById(R.id.choicesdialog_nfcBtn);
		nfcBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(nfcDialog == null)
					nfcDialog = new NFCReaderDialogFragment();
				nfcDialog.setArguments(getArguments());
				nfcDialog.show(getFragmentManager(), "NFC Dialog");
				nfcDialog.reset();
				dismiss();
			}
		});
		
		
		ImageButton wifiBtn = (ImageButton)rootView.findViewById(R.id.choicesdialog_wifiBtn);
		wifiBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				WlanCollectorDialogFragment dialog = new WlanCollectorDialogFragment();
				dialog.setArguments(getArguments());
				dialog.show(getFragmentManager(), "WLAN Dialog");
				
				dismiss();
			}
		});
	}
	
	public void setNFCTagInDialog(String tag){
		
		if(nfcDialog != null){
			nfcDialog.setNFCTag(tag);
		}
	}
	
	public boolean nfcDialogIsOpen(){
		
		if(nfcDialog == null)
			return false;
		
		boolean isOpen = nfcDialog.isAdded();
		return isOpen;
	}
}
