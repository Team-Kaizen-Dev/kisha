package com.kaizen.team.kishaapp.authentication.logic;

import com.kaizen.team.kishaapp.authentication.AuthPreferences;
import com.kaizen.team.kishaapp.authentication.data.AuthResponse;
import com.kaizen.team.kishaapp.authentication.data.User;
import com.satellite.retrofit.retrofit.ServiceHandler;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Jan Paolo Regalado on 6/11/19.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public abstract class AuthLogic {

    private ServiceHandler handler;
    private final String TOKEN_URL = "/oauth/token";

    private static final String GRANT_TYPE = "password";
    private static final String CLIENT_ID = "1hpovz";
    private static final String CLIENT_SECRET = "1twi82";

    public AuthLogic(ServiceHandler handler) {
        this.handler = handler;
    }

    public void registerUser(String username, String password) throws Exception{

    }

    public String getToken() throws Exception {
        if (AuthPreferences.getTokenAge() < System.currentTimeMillis()) {
            return generateNewToken();
        } else {
            return AuthPreferences.getAuthKey();
        }
    }

//    private String generateNewToken() throws Exception {
//        String clientId = CLIENT_ID;
//        String clientSecret = CLIENT_SECRET;
//        String clientCredential = clientId.concat(":").concat(clientSecret);
//        String encodedCredential = new String(Base64.encodeBase64(clientCredential.getBytes()));
//        String credentials = "Basic " + encodedCredential;
//
//        StringBuilder builder = new StringBuilder();
//        builder.append(TOKEN_URL).append("?grant_type=").append(CLIENT_CREDENTIALS).append("&client_id=").append(clientId)
//                .append("&client_secret=").append(clientSecret);
//
//        AuthResponse response = (AuthResponse) handler.callForSyncPostObjectBody(builder.toString(), "", credentials, AuthResponse.class);
//        if(response != null) {
//            AuthPreferences.setAuthKey(response.getAccess_token());
//            long tokenAge = System.currentTimeMillis() + (Integer.parseInt(response.getExpires_in()) * 1000);
//            AuthPreferences.setTokenAge(tokenAge);
//        }else {
//            throw new RuntimeException("Something went wrong. Please try again.");
//        }
//        return AuthPreferences.getAuthKey();
//    }

    private String generateNewToken() throws Exception {
        String clientCredential = CLIENT_ID.concat(":").concat(CLIENT_SECRET);
        String encodedCredential = new String(Base64.encodeBase64(clientCredential.getBytes()));
        String credentials = "Basic " + encodedCredential;

        StringBuilder builder = new StringBuilder();
        builder.append(TOKEN_URL).append("?grant_type=").append(GRANT_TYPE)
                .append("&username=").append(AuthPreferences.getLoginUserName())
                .append("&password=").append(AuthPreferences.getLoginPassword());

        AuthResponse response = (AuthResponse) handler.callForTokenBasic(builder.toString(), "", credentials, AuthResponse.class);
        if(response != null) {
            AuthPreferences.setAuthKey(response.getAccess_token());
            long tokenAge = System.currentTimeMillis() + (Integer.parseInt(response.getExpires_in()) * 1000);
            AuthPreferences.setTokenAge(tokenAge);
        } else {
            throw new RuntimeException("Something went wrong. Please try again.");
        }
        saveAccountId();
        return AuthPreferences.getAuthKey();
    }

    private void saveAccountId() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("/userinfo?access_token=").append(AuthPreferences.getAuthKey());
        User user = (User) handler.callForGetSync(builder.toString(), User.class, AuthPreferences.getAuthKey());
        AuthPreferences.setAccountId(user.getMetadata().getAccountId());
    }

//    private void extractToken(String token) {
//        String[] split_string = token.split("\\.");
//        String base64EncodedBody = split_string[1];
//        JWT jwt = new JWT(token);
//        String body = new String((Base64Utils.decodeFromString(base64EncodedBody)));
//        try {
//            JSONObject obj = new JSONObject(body);
//            String object = obj.getJSONObject("user").toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
