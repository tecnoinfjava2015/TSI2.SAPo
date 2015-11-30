(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ChatController', ChatController);
    ChatController.$inject = ['$cookies', '$scope', '$location'];
    /* @ngInject */
    function ChatController($cookies, $scope, $location) {

    	var serverHost = $location.host();
    	var serverPort = $location.port();
        var userTwitter = $cookies.getObject('sapoUser');
        var userAvatar = $cookies.get('userAvatar');
        $scope.title = "SAPo-Chat " + userTwitter.name;
        var wsocket;
        var serviceLocation = "ws://"+serverHost+":"+serverPort+"/SAPo-FO/ChatEndpoint/";
        var $nickName;
        var $avatar;
        var $message; 
        var $chatWindow; 
        var room = '';  
        room = $cookies.get("sapoCurrentVirtualStorageName");
        console.log(room);

        function onMessageReceived(evt) {
            var msg = JSON.parse(evt.data); // native API
            if (msg.avatar===userAvatar) {
                var $messageLine = $('<md-list-item class="md-2-line" ng-repeat="item in todos"><img src='+msg.avatar+' class="md-avatar" alt="'+msg.sender+'" /><div class="md-list-item-text" layout="column"><h4>'+msg.sender+'</h4><p>'+msg.message+'</p></div></md-list-item><md-divider ></md-divider>');//('<ul>' + msg.sender + ": " + msg.message + '</ul>');
            }else{
                var $messageLine = $('<md-list-item class="md-2-line" ng-repeat="item in todos"><div class="md-list-item-text" layout="column"><h4>'+msg.sender+'</h4><p>'+msg.message+'</p></div><img src='+msg.avatar+' class="md-avatar" alt="'+msg.sender+'" /></md-list-item><md-divider ></md-divider>');//('<ul>' + msg.sender + ": " + msg.message + '</ul>');                
            }
            
            $chatWindow.append($messageLine);
            
            $("#responseContainer").scrollTop($("#responseContainer")[0].scrollHeight); 
        } 

        function sendMessage() {
            var msg = '{"message":"' + $message.val() + '", "sender":"' + $nickName +'", "avatar":"'+$avatar + '"}';
            wsocket.send(msg);
            $message.val('').focus(); 
        }
 
        function connectToChatserver() {
        	
//            room = $cookies.get("sapoCurrentVirtualStorageName");//'Almacen';
            wsocket = new WebSocket(serviceLocation + room);
            wsocket.onmessage = onMessageReceived;
        }

        function leaveRoom() {
            wsocket.close();
            $chatWindow.empty();
            $('.chat-wrapper').hide();
            $('.chat-signin').show();
            $nickName.focus();
        }

        $(document).ready(function() {


 
//            console.log(userTwitter);
            $nickName = userTwitter.name;
            $avatar = userAvatar;
            $message = $('#message');
            $chatWindow = $('#response');
            $('.chat-wrapper').hide();
            
            $('#sendDiv').ready(function(){
            	var maxHeight = ($( window ).height())-($('#sendDiv').height())-($('#chatDiv').position().top);
            	$('#responseContainer').height(maxHeight-85);
            });
            
            
            
            connectToChatserver();
            $('.chat-signin').hide();
            $('.chat-wrapper').show();
            $message.focus();

            $('#do-chat').submit(function(evt) {
                evt.preventDefault();
                sendMessage()
            });

            $('#leave-room').click(function() {
                leaveRoom();
            });
        });
    }
})(); 
