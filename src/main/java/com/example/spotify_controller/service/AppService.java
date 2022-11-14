package com.example.spotify_controller.service;

import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.AuthorizationScope;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.player.PauseUsersPlaybackRequest;
import se.michaelthelin.spotify.requests.data.player.SkipUsersPlaybackToNextTrackRequest;
import se.michaelthelin.spotify.requests.data.player.SkipUsersPlaybackToPreviousTrackRequest;
import se.michaelthelin.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

import java.net.URI;

@Service
public class AppService {
    private static final String clientID = "f9fa2f3c2ff041c3a88230edd63986c3";
    private static final String clientSecret = "676cd067367748789e75493473fd7da3";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    public static String code = "";

    public static final SpotifyApi spotifyAPI = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    public static AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyAPI.authorizationCodeUri()
            .scope(AuthorizationScope.USER_MODIFY_PLAYBACK_STATE)
            .build();
    public static AuthorizationCodeRequest authorizationCodeRequest;
    public static AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;
    public static StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest;
    public static PauseUsersPlaybackRequest pauseUsersPlaybackRequest;
    public static SkipUsersPlaybackToNextTrackRequest skipUsersPlaybackToNextTrackRequest;
    public static SkipUsersPlaybackToPreviousTrackRequest skipUsersPlaybackToPreviousTrackRequest;

    public String getAuthUri() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());

        return uri.toString();
    }

    public void initCode(String code) {
        this.code = code;

        authorizationCodeRequest  = spotifyAPI.authorizationCode(code)
                .build();
        authorizationCodeRefreshRequest = spotifyAPI.authorizationCodeRefresh()
                .build();

    }

    private void initializeRequests() {
        startResumeUsersPlaybackRequest = spotifyAPI
                .startResumeUsersPlayback()
                .build();
        pauseUsersPlaybackRequest = spotifyAPI
                .pauseUsersPlayback()
                .build();

        skipUsersPlaybackToNextTrackRequest = spotifyAPI
                .skipUsersPlaybackToNextTrack()
                .build();
        skipUsersPlaybackToPreviousTrackRequest = spotifyAPI
                .skipUsersPlaybackToPreviousTrack()
                .build();
    }

    public void getAccessToken() {
        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            System.out.println(authorizationCodeCredentials.getAccessToken());
            spotifyAPI.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyAPI.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            initializeRequests();
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

        } catch (Exception e) {
            // NOT YET IMPLEMENTED
            System.out.println("authCode Error");
            System.out.println(e.getMessage());
        }
    }

    public void initAccessToken(String accessToken) {
        spotifyAPI.setAccessToken(accessToken);
        initializeRequests();
    }

    public void refreshCode() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            spotifyAPI.setAccessToken(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (Exception e) {
            System.out.println("Refresh Auth Error");
            System.out.println(e.getMessage());
        }
    }

    public void togglePlay() {
        try {
            final String string = startResumeUsersPlaybackRequest.execute();

            System.out.println("Null: " + string);
        } catch (Exception e) {
            System.out.println("Toggle Play Error");
            System.out.println(e.getMessage());
        }
    }

    public void togglePause() {
        try {
            final String string = pauseUsersPlaybackRequest.execute();

            System.out.println("Null: " + string);
        } catch (Exception e) {
            System.out.println("Toggle Pause Error");
            System.out.println(e.getMessage());
        }
    }

    public void nextTrack() {
        try {
            skipUsersPlaybackToNextTrackRequest.execute();
        } catch (Exception e) {
            System.out.println("Next Track Error");
            System.out.println(e.getMessage());
        }
    }

    public void previousTrack() {
        try {
            skipUsersPlaybackToPreviousTrackRequest.execute();
        } catch (Exception e) {
            System.out.println("Previous Track Error");
            System.out.println(e.getMessage());
        }
    }
}
