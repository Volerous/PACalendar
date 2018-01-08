import QtQuick 2.0
import QtQuick.Controls 2.1
import QtQuick.Controls.Material 2.1

ApplicationWindow {
    visible: true
    width: 320; height: 480
    Material.theme: Material.Dark
    Material.accent: Material.Purple
    id:page

    Column {
        Text {
                id: helloText
                text: "Hello world!"
                y: 30
                anchors.horizontalCenter: page.horizontalCenter
                font.pointSize: 24; font.bold: true
            }
        Button {
                objectName: "test_button"
                text: "Button"
            }
    }
    
}
