package de.whs.fia.studmap.collector.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import de.whs.fia.studmap.collector.MainActivity;
import de.whs.fia.studmap.collector.R;
import de.whs.studmap.client.core.data.Floor;
import de.whs.studmap.client.core.data.Map;
import de.whs.studmap.client.listener.OnGenericTaskListener;
import de.whs.studmap.client.listener.OnGetFloorsTaskListener;
import de.whs.studmap.client.tasks.GetFloorsTask;
import de.whs.studmap.client.tasks.GetMapsTask;

public class MapFloorSelectorFragment extends Fragment implements
		OnGetFloorsTaskListener, OnGenericTaskListener<List<Map>> {

	private ArrayAdapter<Map> mMapsAdapter;
	private ArrayAdapter<Floor> mFloorsAdapter;
	private Spinner mFloorSpinner;
	private Spinner mMapSpinner;
	protected Map mSelectedMap;
	protected Floor mSelectedFloor;

	OnMapFloorSelectedListener mCallback;

	public static MapFloorSelectorFragment newInstance() {

		MapFloorSelectorFragment f = new MapFloorSelectorFragment();

		return f;
	}

	public interface OnMapFloorSelectedListener {

		public void onMapFloorSelected(Map map, Floor floor);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = (View) inflater.inflate(
				R.layout.fragment_mapfloorselector, container, false);

		((MainActivity) getActivity()).openProgressDialog("Lade");

		initializeSpinner(rootView);

		initializeButton(rootView);

		fillMapSpinner();

		((MainActivity) getActivity()).closeProgressDialog();

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnMapFloorSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	private void fillMapSpinner() {

		GetMapsTask mTask = new GetMapsTask(this);
		mTask.execute((Void) null);
	}

	private void fillFloorSpinner(int mapId) {

		GetFloorsTask mTask = new GetFloorsTask(this, mapId);
		mTask.execute((Void) null);
	}

	private void initializeButton(View rootView) {
		Button loadFloorplanBtn = (Button) rootView
				.findViewById(R.id.mapFloorSelector_LoadBtn_id);
		loadFloorplanBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mSelectedMap == null) {
					Toast.makeText(getActivity(),
							"Es wird eine Map f�r Messungen ben�tigt!",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (mSelectedFloor == null) {
					Toast.makeText(getActivity(),
							"Es wird ein Floor f�r Messungen ben�tigt!",
							Toast.LENGTH_LONG).show();
					return;
				}

				mCallback.onMapFloorSelected(mSelectedMap, mSelectedFloor);
			}
		});
	}

	private void initializeSpinner(View rootView) {

		mMapsAdapter = new ArrayAdapter<Map>(rootView.getContext(),
				android.R.layout.simple_spinner_item);
		mMapsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mMapSpinner = (Spinner) rootView
				.findViewById(R.id.mapFloorSelector_mapSpinner);

		mMapSpinner.setAdapter(mMapsAdapter);

		mMapSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				mSelectedMap = (Map) parent.getItemAtPosition(pos);

				fillFloorSpinner(mSelectedMap.getId());

				mFloorSpinner.setClickable(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

				mSelectedMap = null;
				mFloorSpinner.setClickable(false);
			}
		});

		mFloorsAdapter = new ArrayAdapter<Floor>(rootView.getContext(),
				android.R.layout.simple_spinner_item);
		mFloorsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mFloorSpinner = (Spinner) rootView
				.findViewById(R.id.mapFloorSelector_floorSpinner);

		mFloorSpinner.setAdapter(mFloorsAdapter);

		mFloorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				mSelectedFloor = (Floor) parent.getItemAtPosition(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

				mSelectedFloor = null;
			}
		});
	}

	@Override
	public void onGetFloorsSuccess(List<Floor> floors) {
		if (floors != null)
			mFloorsAdapter.addAll(floors);
	}

	@Override
	public void onGetFloorsError(int responseError) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetFloorsCanceled() {
		// TODO Auto-generated method stub

	}

	//GetMapsTask
	@Override
	public void onSuccess(List<Map> maps) {
		if (maps != null)
			mMapsAdapter.addAll(maps);
	}

	//GetMapsTask
	@Override
	public void onError(int responseError) {
		// TODO Auto-generated method stub
		
	}

	//GetMapsTask
	@Override
	public void onCanceled() {
		// TODO Auto-generated method stub
		
	}
}
