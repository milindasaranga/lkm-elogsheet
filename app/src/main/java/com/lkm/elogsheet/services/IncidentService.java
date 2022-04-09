package com.lkm.elogsheet.services;

import android.content.Context;

import com.lkm.elogsheet.models.Incident;
import com.lkm.elogsheet.utils.CommonUtil;
import com.lkm.elogsheet.utils.Constants;
import com.lkm.elogsheet.utils.DateUtil;
import com.lkm.elogsheet.utils.DocTypes;

public class IncidentService {

    public void saveIncidentDetails(Incident incident, Context context){
        String userId = CommonUtil.getClientID(context);
        incident.id= DocTypes.INCIDENT + Constants.DOC_ID_SEPERATOR + DateUtil.getDateAndTime();
        incident.audit=CommonUtil.getAudit(incident.getAudit(),userId);
        DBService.getInstance().write(incident);
    }


}
