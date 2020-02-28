package com.example.apollotestproj.ui.dashboard;

import android.app.Activity;
import android.media.AsyncPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.apollographqlandroid.LoginOrRegisterMutation;
import com.example.apollographqlandroid.RequestOtpQuery;
import com.example.apollotestproj.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import okhttp3.OkHttpClient;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    Button buttonSubmit;
    EditText txtOTP;
    TextView text_dashboard;
    private static final String BASE_URL = "http://3.17.72.41:8080/graphql";
    OkHttpClient okHttpClient;
    ApolloClient apolloClient;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        buttonSubmit = (Button) root.findViewById(R.id.buttonSubmit);
        txtOTP = (EditText) root.findViewById(R.id.txtOTP);
        text_dashboard = (TextView) root.findViewById(R.id.text_dashboard);

        okHttpClient = new OkHttpClient.Builder().build();

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoginOrRegisterMutation login = LoginOrRegisterMutation.builder()
                        .phone("9741299367").otp(txtOTP.getText().toString()).lang("EN").latitude(15.5).longitude(15.5).build();

                apolloClient.mutate(login).enqueue(new ApolloCall.Callback<LoginOrRegisterMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull final Response<LoginOrRegisterMutation.Data> response) {
//                        LoginOrRegisterMutation.LoginOrRegister loginReg = response.data().loginOrRegister();
//                         loginReg.name();
//                        loginReg.sessionToken();
//                        loginReg.placeName();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("Success OTP "," Success OTP " + response.toString() + " Data " + response.data());
                                text_dashboard.setText(" Success OTP " + response.toString() + " Data " + response.data());
                            }
                        });

                    }

                    @Override
                    public void onFailure(@NotNull final ApolloException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("Fail OTP"," Fail Otp " + e.getMessage());
                                text_dashboard.setText(" Fail Otp " + e.getMessage());
                            }
                        });

                    }
                });
            }
        });

        RequestOtpQuery feedQuery = RequestOtpQuery.builder().hash("mystrongpassword@123").phone("9741299367").build();
        apolloClient.query(feedQuery).enqueue(new ApolloCall.Callback<RequestOtpQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<RequestOtpQuery.Data> response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Success send OTP"," Success send OTP " + response.toString() + " Data " + response.data());
                        text_dashboard.setText(" Success send OTP " + response.toString() + " Data " + response.data());

                    }
                });
            }
            @Override
            public void onFailure(@NotNull final ApolloException e) {
                Log.v(" Fail send Otp ", e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_dashboard.setText(" Fail send Otp " + e.getMessage());
                    }
                });

            }
        });



        return root;
    }

//    @Override
//    public void onClick(View view) {
//        MyQuery feedQuery = MyQuery.builder().build();
//        apolloClient.query(feedQuery).enqueue(new ApolloCall.Callback<MyQuery.Data>(){
//
//        });

//    }
}

//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        testBtn = root.findViewById(R.id.testBtn);
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//apolloClient.query(feedQuery).enqueue(new ApolloCall.Callback<RequestOtpQuery.Data>() {
//@Override
//public void onResponse(@NotNull Response<RequestOtpQuery.Data> response) {
//
//        Log.v("Response", "Response " + response);
//        Log.v("Response1", "Response1 " + response.data());
////                getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////
////                            testBtn.setText("Changed");
////                        }
////                    }
////
////                );
//        }
//@Override
//public void onFailure(@NotNull ApolloException e) {
//        Log.v("Error", "Error " + e.getMessage());
//        }
//        });
