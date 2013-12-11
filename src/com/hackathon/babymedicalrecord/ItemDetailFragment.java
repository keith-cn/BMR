package com.hackathon.babymedicalrecord;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableDeleteCallback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment implements View.OnClickListener {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;
	

	private MobileServiceClient mClient;
	private MobileServiceTable<BMRData> mBMRData;
	BMRData mData;
	
//	private TextView mDate;
//	private TextView mSymptomName;
//	private TextView mSymptomTemperature;
//	private TextView mHospital;
//	private TextView mDoctor;
//	private TextView mCost;
//	private TextView mMedicine;
//	private TextView mDiagnosis;
//	private TextView mSymptomDetail;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
			mData = (BMRData) getArguments().getSerializable(ARG_ITEM_ID);
			Log.e("Keith", "getArg: " + mData.getHospital());
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);


		rootView.findViewById(R.id.details_delete).setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.detail_date)).setText(mData.getSymptomYear() + "-" + mData.getSymptomMonth()+"-"+mData.getSymptomDay());
		((TextView) rootView.findViewById(R.id.detail_symptom_name)).setText(mData.getSymptomName());
		((TextView) rootView.findViewById(R.id.detail_temperature)).setText(mData.getSymptomTemperature());
		((TextView) rootView.findViewById(R.id.detail_hospital)).setText(mData.getHospital());
		((TextView) rootView.findViewById(R.id.detail_doctor)).setText(mData.getDoctor());
		((TextView) rootView.findViewById(R.id.detail_cost)).setText(String.valueOf(mData.getCost()));
		((TextView) rootView.findViewById(R.id.detail_medicine)).setText(mData.getMedicine());
		((TextView) rootView.findViewById(R.id.detail_diagnosis)).setText(mData.getDiagnosis());
		((TextView) rootView.findViewById(R.id.detail_others)).setText(mData.getSymptomDetail());
		
		
//		mSymptomName = (TextView) rootView.findViewById(R.id.detail_symptom_name);
//		mSymptomTemperature = (TextView) rootView.findViewById(R.id.detail_temperature);
//		mHospital = (TextView) rootView.findViewById(R.id.detail_hospital);
//		mDoctor = (TextView) rootView.findViewById(R.id.detail_doctor);
//		mCost = (TextView) rootView.findViewById(R.id.detail_cost);
//		mMedicine = (TextView) rootView.findViewById(R.id.detail_medicine);
//		mDiagnosis = (TextView) rootView.findViewById(R.id.detail_diagnosis);
//		mSymptomDetail = (TextView) rootView.findViewById(R.id.detail_others);

		mClient = BMRUtil.getMobileService(getActivity(), new ProgressFilter());
		mBMRData = BMRUtil.getBMRDataTable();

		return rootView;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.details_delete) {
			
			mBMRData.delete(mData.getId(), new TableDeleteCallback() {
		        public void onCompleted(Exception exception, 
		                ServiceFilterResponse response) {
		            if(exception == null){
		            	BMRUtil.createAndShowDialog("Delete done.",
								"Babay Medical Record", getActivity());
		            }
		            
		            getActivity().finish();
		        }
		    });
		}	
	}

	private class ProgressFilter implements ServiceFilter {

		@Override
		public void handleRequest(ServiceFilterRequest request,
				NextServiceFilterCallback nextServiceFilterCallback,
				final ServiceFilterResponseCallback responseCallback) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// if (mProgressBar != null)
					// mProgressBar.setVisibility(ProgressBar.VISIBLE);
				}
			});

			nextServiceFilterCallback.onNext(request,
					new ServiceFilterResponseCallback() {

						@Override
						public void onResponse(ServiceFilterResponse response,
								Exception exception) {
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// if (mProgressBar != null)
									// mProgressBar.setVisibility(ProgressBar.GONE);
								}
							});

							if (responseCallback != null)
								responseCallback
										.onResponse(response, exception);
						}
					});
		}
	}
}
