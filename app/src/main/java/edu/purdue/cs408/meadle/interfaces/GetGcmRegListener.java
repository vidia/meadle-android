package edu.purdue.cs408.meadle.interfaces;

/**
 * Created by kyle on 9/22/14.
 * Interface used for GcmManager.getRegId, used when one needs to get a GCM registration ID
 * If will either one, be called right away if the regId exists in shared Preferences
 * Or It will be called by the OnGetGcmRegIdFinished Listener (called from the corresponding asyncTask) is called
 */

public interface GetGcmRegListener {
    public void OnRegIdReceived(String regId);
}
