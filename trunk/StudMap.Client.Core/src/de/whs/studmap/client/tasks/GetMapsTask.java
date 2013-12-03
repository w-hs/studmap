package de.whs.studmap.client.tasks;

import java.net.ConnectException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import de.whs.studmap.client.core.data.Constants;
import de.whs.studmap.client.core.data.Map;
import de.whs.studmap.client.core.web.ResponseError;
import de.whs.studmap.client.core.web.Service;
import de.whs.studmap.client.core.web.WebServiceException;
import de.whs.studmap.client.listener.OnGenericTaskListener;

public class GetMapsTask extends AsyncTask<Void, Void, Boolean> {

	private OnGenericTaskListener<List<Map>> mCallback;
	private List<Map> mMaps;

	public GetMapsTask(OnGenericTaskListener<List<Map>> listener) {

		this.mCallback = listener;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			mMaps = Service.getMaps();
			if (mMaps == null || mMaps.size() == 0)
				return false;
			return true;
		} catch (WebServiceException e) {
			Log.d(Constants.LOG_TAG_MAIN_ACTIVITY,
					"GetDataTask - WebServiceException");

			JSONObject jObject = e.getJsonObject();
			try {
				int errorCode = jObject.getInt(Service.RESPONSE_ERRORCODE);
				mCallback.onError(errorCode);
			} catch (JSONException ignore) {
				Log.d(Constants.LOG_TAG_MAIN_ACTIVITY,
						"GetDataTask - Parsing the WebServiceException failed!");
			}
		} catch (ConnectException e) {
			Log.d(Constants.LOG_TAG_MAIN_ACTIVITY,
					"GetDataTask - ConnectException");
			mCallback.onError(ResponseError.UnknownError);
		}
		return false;
	}

	@Override
	protected void onPostExecute(final Boolean success) {

		if (success) {
			mCallback.onSuccess(mMaps);
		} else {
			mCallback.onError(ResponseError.UnknownError);
		}
	}

	@Override
	protected void onCancelled() {

		mCallback.onCanceled();
	}
}