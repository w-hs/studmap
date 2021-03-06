﻿using StudMap.Core.Graph;

namespace StudMap.Core.Information
{
    public class NodeInformation
    {
        public NodeInformation(Node node, string displayName, string roomName, PoI poI, string qrCode, string nfcTag)
        {
            Node = node;
            DisplayName = displayName;
            RoomName = roomName;
            PoI = poI;
            NFCTag = nfcTag;
            QRCode = qrCode;
        }

        public NodeInformation()
        {
            DisplayName = "";
            RoomName = "";
            Node = new Node();
            PoI = new PoI();
            NFCTag = "";
            QRCode = "";
        }

        public NodeInformation(PoI poI, string displayName = "", string roomName = "", string qrCode = "",
                               string nfcTag = "")
        {
            DisplayName = displayName;
            RoomName = roomName;
            PoI = poI;
            NFCTag = nfcTag;
            QRCode = qrCode;
        }

        public NodeInformation(string displayName, string roomName, PoI poI)
        {
            DisplayName = displayName;
            RoomName = roomName;
            PoI = poI;
        }

        public string DisplayName { get; set; }
        public string RoomName { get; set; }
        public Node Node { get; set; }
        public PoI PoI { get; set; }
        public string QRCode { get; set; }
        public string NFCTag { get; set; }
    }
}