(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ChatController', ChatController);
    ChatController.$inject = ['$cookies', '$scope', '$location'];
    /* @ngInject */
    function ChatController($cookies, $scope, $location) {


        var wsocket;
        var serviceLocation = "ws://localhost:8080/SAPo-FO/ChatEndpoint/";
        var $nickName;
        var $message;
        var $chatWindow; 
        var room = '';

        function onMessageReceived(evt) {
            var msg = JSON.parse(evt.data); // native API
            var $messageLine = $('<ul>' + msg.sender + ": " + msg.message + '</ul>');
            $chatWindow.append($messageLine);
            $("#response").scrollTop($("#response")[0].scrollHeight);
        }

        function sendMessage() {
            var msg = '{"message":"' + $message.val() + '", "sender":"' + $nickName + '"}';
            wsocket.send(msg);
            $message.val('').focus();
        }

        function connectToChatserver() {
            room = 'Almacen';
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
            $nickName = 'lufasoch';
            $message = $('#message');
            $chatWindow = $('#response');
            $('.chat-wrapper').hide();

            connectToChatserver();
            $('.chat-wrapper h2').text('Chat # ' + $nickName + "@" + room);
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
