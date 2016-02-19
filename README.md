# dfk
dfkj
-------------------------------------------------------------- 
App Names: dfkj, Dfk, sendDfk
App Types: Bash Script, java, Bash Script
Last Update: 12/01/2016
Writen by A. Murat Ergin
-------------------------------------------------------------- 
Description: 
dfkj    : This script firstly runs unix dfk command then runs Dfk app.
Dfk     : parses df command output with dfk.conf parameters and creates html output
sendDfk : sends e-mail to users with attachment of html file which has created by Dfk 
Requirtments:
-------------------------------------------------------------
/usr/bin/bash 
java SE JDK 1.5 or above

Execute (UNIX)
-------------------------------------------------------------

$ df -k > $HOME/Script/dfk_$HOSTNAME.txt

$ df -h >> $HOME/Script/dfk_$HOSTNAME.txt

$ rm $HOME/Script/dfk_$HOSTNAME.html

$ java -classpath $HOME/Script Dfk $HOME/Script/dfk_$HOSTNAME.txt 6 $HOME/Script/dfk.conf

$ $HOME/Script/sendDfk e-mail_address



