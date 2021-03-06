package de.whs.studmap.client.core.data;

public interface Constants {
    	//TEST
		 static final int MAP_ID = 3;
	
		//Common responses
		 static final String RESPONSE_STATUS = "Status";
		 static final String RESPONSE_ERRORCODE = "ErrorCode";

		 static final String RESPONSE_PARAM_LIST = "List";
		 static final String RESPONSE_PARAM_OBJECT = "Object";
		
		//Methods
		 static final String METHOD_LOGIN = "Login";
		 static final String METHOD_LOGOUT = "Logout";
		 static final String METHOD_REGISTER = "Register";
		 static final String METHOD_GET_POIS = "GetPoIsForMap";
		 static final String METHOD_GET_ROOMS = "GetRoomsForMap";
		 static final String METHOD_GET_FLOORS = "GetFloorsForMap";
		 static final String METHOD_GET_MAPS = "GetMaps";
		 static final String METHOD_GET_NODE_INFO_FOR_NODE = "GetNodeInformationForNode";
		 static final String METHOD_GET_NODE_FOR_QR_CODE = "GetNodeForQRCode";
		 static final String METHOD_GET_NODE_FOR_NFC_TAG = "GetNodeForNFC";
		 static final String METHOD_SAVEFINGERPRINTFORNODE = "SaveFingerprintForNode";
		 static final String METHOD_GETNODEFORFINGERPRINT = "GetNodeProbabiltyForScan";
		 static final String METHOD_SAVE_NFCTAG_FOR_NODE = "SaveNFCForNode";
			
		//UserController
		 static final String REQUEST_PARAM_USERNAME = "userName";
		 static final String REQUEST_PARAM_PASSWORD = "password";
		 
		 //MapController
		 static final String REQUEST_PARAM_MAPID = "mapId";
		 static final String REQUEST_PARAM_NODEID = "nodeId";
		 static final String REQUEST_PARAM_QR_CODE = "qrCode";
		 static final String REQUEST_PARAM_NFC_TAG = "nfcTag";
		 
		 //FingerprintController
		 static final String REQUEST_PARAM_FACTOR = "factor";
		
		//Node
		 static final String RESPONSE_PARAM_NODE_NODE = "Node";
		 static final String RESPONSE_PARAM_NODE_ROOMNAME = "RoomName";
		 static final String RESPONSE_PARAM_NODE_DISPLAYNAME = "DisplayName";
		 static final String RESPONSE_PARAM_NODE_NODE_ID = "NodeId";
		 static final String RESPONSE_PARAM_NODE_FLOOR_ID = "FloorId";
		 static final String RESPONSE_PARAM_NODE_ID = "Id";
		 
		 //Map
		 static final String RESPONSE_PARAM_MAP_ID = "Id";
		 static final String RESPONSE_PARAM_MAP_NAME = "Name";
		
		//Floor
		 static final String RESPONSE_PARAM_FLOOR_ID = "Id";
		 static final String RESPONSE_PARAM_FLOOT_MAPID = "MapId";
		 static final String RESPONSE_PARAM_FLOOR_NAME = "Name";
		 static final String RESPONSE_PARAM_FLOOR_IMAGE_URL = "ImageUrl";
		
		 //PoI
		 static final String RESPONSE_PARAM_POI_NAME = "Name";
		 static final String RESPONSE_PARAM_POI_TYPEID = "Id";
		 static final String RESPONSE_PARAM_POI_ROOM = "Room";
		 static final String RESPONSE_PARAM_POI_POI = "PoI";
		 static final String RESPONSE_PARAM_POI_TYPE = "Type";
		 static final String RESPONSE_PARAM_POI_DESCRIPTION = "Description";
		 
		//Urls
		 static final String URL_USER = "http://193.175.199.115:80/StudMapService/api/Users/";
		 static final String URL_MAPS = "http://193.175.199.115:80/StudMapService/api/Maps/"; 
		 static final String URL_FINGERPRINT = "http://193.175.199.115:80/StudMapService/api/Fingerprint/";
		 
		 //Log tags
		 static final String LOG_TAG_WEBSERVICE = "WebService";
		 static final String LOG_TAG_POI__ACTIVITY = "PoI Dialog";
		 static final String LOG_TAG_MAIN_ACTIVITY = "Main Activity";
		 static final String LOG_TAG_LOGIN_DIALOG = "Login Dialog";
		 static final String LOG_TAG_REGISTER_DIALOG = "Register Dialog";
		 static final String LOG_TAG_POSITION_ACTIVITY = "Position Dialog";
		
}
