package de.t_dankworth.secscanqr.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.t_dankworth.secscanqr.R;


/**
 * Created by Thore Dankworth
 * Last Update: 17.01.2019
 * Last Update by Thore Dankworth
 *
 * This class handles the functionality of the buttons like share, reset, copy etc.
 */

public class ButtonHandler {

    /**
     * This method resets all the information that were shown on the MainActivty
     * @param tvInformation = TextView were the qrcode is shown
     * @param tvFormat = TextView were the qrcode format is shown
     * @param mLabelInformation = TextView were the qrcode headline is shown
     * @param mLabelFormat = TextView were the qrcode format headline is shown
     * @param qrcode = the qrcode as a String - Needs to be reset for orientation switches
     * @param format= the qrcode format as a String - Needs to be reset for orientation switches
     * @param buttonContainer = The Container as a LinearLayout with all the Buttons
     */
    public static void resetScreenInformation(final TextView tvInformation, final TextView tvFormat, final TextView mLabelInformation, final TextView mLabelFormat, final String qrcode, final String format, final BottomNavigationView buttonContainer) {
        tvInformation.setText(R.string.default_text_main_activity);
        tvFormat.setText("");
        tvFormat.setVisibility(View.GONE);
        mLabelInformation.setVisibility(View.GONE);
        mLabelFormat.setVisibility(View.GONE);
        qrcode = "";
        format = "";
        buttonContainer.setVisibility(View.INVISIBLE);
    }

    /**
     * This method copies the information of the QR-Code to the clipboard
     * @param tv = TextView were the qrcode is shown
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called. Needed for Toats and Clipboard
     */
    public static void copyToClipboard(final TextView tv, final String qrcode, final Activity activity) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tv.getText(), qrcode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity, activity.getResources().getText(R.string.notice_clipoard), Toast.LENGTH_LONG).show();
    }

    /**
     * This method handles the sharing functionality
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called. Needed for sharing intent
     */
    public static void shareTo(final String qrcode, final Activity activity) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, qrcode);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.send_to)));
    }

    /**
     * ONLY for history: The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called.Needed for Toast and web intent
     */
    public static void openInWeb(final String qrcode, final Activity activity) {
        if (qrcode.equals("")) {
            Toast.makeText(activity.getApplicationContext(), activity.getResources().getText(R.string.error_scan_first), Toast.LENGTH_SHORT).show();
        } else {
            try {
                Uri uri = Uri.parse(qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                String search_engine = prefs.getString("pref_search_engine", "");
                String tempUrl;
                switch (search_engine) {
                    case "1":
                        tempUrl = "https://www.bing.com/search?q=";
                        break;
                    case "2":
                        tempUrl = "https://duckduckgo.com/?q=";
                        break;
                    case "3":
                        tempUrl = "http://www.google.com/#q=";
                        break;
                    case "4":
                        tempUrl = "https://www.qwant.com/?q=";
                        break;
                    case "5":
                        tempUrl = "https://lite.qwant.com/?q=";
                        break;
                    case "6":
                        tempUrl = "https://www.startpage.com/do/dsearch?query=";
                        break;
                    case "7":
                        tempUrl = "https://search.yahoo.com/search?p=";
                        break;
                    case "8":
                        tempUrl = "https://www.yandex.ru/search/?text=";
                        break;
                    default:
                        tempUrl = "http://www.google.com/#q=";
                        break;
                }
                Uri uri = Uri.parse(tempUrl + qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * @param qrcode = the qrcode as a String
     * @param format = format of the code
     * @param activity = Activty were the method was called.Needed for Toast and web intent
     */
    public static void openInWeb(final String qrcode, final String format, final Activity activity) {
        if (qrcode.equals("")) {
            Toast.makeText(activity.getApplicationContext(), activity.getResources().getText(R.string.error_scan_first), Toast.LENGTH_SHORT).show();
        } else {
            try {
                Uri uri = Uri.parse(qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                String search_engine = prefs.getString("pref_search_engine", "");
                String barcode_engine = prefs.getString("pref_barcode_search_engine", "");
                String tempUrl;
                if (barcode_engine.equals("0")) {
                    switch (search_engine) {
                        case "1":
                            tempUrl = "https://www.bing.com/search?q=";
                            break;
                        case "2":
                            tempUrl = "https://duckduckgo.com/?q=";
                            break;
                        case "3":
                            tempUrl = "http://www.google.com/#q=";
                            break;
                        case "4":
                            tempUrl = "https://www.qwant.com/?q=";
                            break;
                        case "5":
                            tempUrl = "https://lite.qwant.com/?q=";
                            break;
                        case "6":
                            tempUrl = "https://www.startpage.com/do/dsearch?query=";
                            break;
                        case "7":
                            tempUrl = "https://search.yahoo.com/search?p=";
                            break;
                        case "8":
                            tempUrl = "https://www.yandex.ru/search/?text=";
                            break;
                        default:
                            tempUrl = "http://www.google.com/#q=";
                            break;
                    }
                } else {
                    if (format.equals("QR_CODE") || format.equals("AZTEC")) {
                        switch (search_engine) {
                            case "1":
                                tempUrl = "https://www.bing.com/search?q=";
                                break;
                            case "2":
                                tempUrl = "https://duckduckgo.com/?q=";
                                break;
                            case "3":
                                tempUrl = "http://www.google.com/#q=";
                                break;
                            case "4":
                                tempUrl = "https://www.qwant.com/?q=";
                                break;
                            case "5":
                                tempUrl = "https://lite.qwant.com/?q=";
                                break;
                            case "6":
                                tempUrl = "https://www.startpage.com/do/dsearch?query=";
                                break;
                            case "7":
                                tempUrl = "https://search.yahoo.com/search?p=";
                                break;
                            case "8":
                                tempUrl = "https://www.yandex.ru/search/?text=";
                                break;
                            default:
                                tempUrl = "http://www.google.com/#q=";
                                break;
                        }
                    } else {
                        switch (barcode_engine) {
                            case "1":
                                tempUrl = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
                                break;
                            case "2":
                                tempUrl = "https://www.codecheck.info/product.search?q=";
                                break;
                            default:
                                tempUrl = "http://www.google.com/#q=";
                                break;
                        }
                    }
                }

                Uri uri = Uri.parse(tempUrl + qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        }
    }
}
