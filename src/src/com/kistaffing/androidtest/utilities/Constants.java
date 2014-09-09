package com.kistaffing.androidtest.utilities;

public class Constants {
	public static final String API_KEY = "23bbznbm8bedxgk5x9bxds2s";
	public static final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";
	public static final String MOVIES_URL = BASE_URL + "/movies.json";

	public static final String PARAM_API_KEY = "apikey";
	public static final String PARAM_SEARCH_QUERY = "q";
	public static final String PARAM_PAGE_LIMIT = "page_limit";
	public static final String PARAM_PAGE_NUMBER = "page";

	public static final int REQUEST_SEARCH = 101;
	public static final int REQUEST_IMAGE_DOWNLOAD = 102;
	public static final int DIALOG_SEARCH_FAILED = 201;
	public static final int CONNECTION_PERIOD = 30000;
	public static final String RESPONSE_CONNECTION_TIMEOUT = "Connection timeout.";
	public static final String EXTRA_RESULT = "moviesResult";
}

