# ChattServer
(Looking the client version? You're in the wrong place, sorry.)

**ChattServer** is the server jar needed to run a **Chatt** server.\
Plugin support (equivalent to a "Bot" in Discord) is not yet being developed right now, though.

## Table of contents
1. [Disclaimer](#Disclaimer)
2. [How to use](#How-to-use)
3. [How to build](#How-to-build)

## Disclaimer
1. This product **IS NOT AN ALTERNATIVE TO OTHER CHAT CLIENTS/SERVERS**;
this product **must** be used as a _secondary_ chat client.
2. As Chatt products do _not_ have a TOS against bad usage of the product (other than hacking other users),
many people _may_ do the following, but not limited to:
   - Engaging in hate speech or discriminatory behavior.
   - Sharing or promoting explicit or illegal content.
   - Harassing or stalking other users.
   - Engaging in fraudulent activities or scams.
   - Violating intellectual property rights by sharing copyrighted material without permission.
   - Spreading misinformation or engaging in disinformation campaigns.
   - Engaging in cyberbullying or online harassment.
   - Invading others' privacy by sharing personal information without consent.
   - Promoting or organizing illegal activities, such as drug trafficking or terrorism.
3. ChattServer does **NOT** log messages, neither in console nor log files, sent by players;
this is considered to be telemetry and we want Chatt products to be privacy-focused.
Consider joining the server itself.

## How to use
Provided that you understand the risks provided in the disclaimer,
here is a step-by-step tutorial on how to start the server yourself:
1. Install Java 17.0.3 or higher.
2. Download the `server-[VERSION].jar` file.
   - Although you _can_ rename the `server-[VERSION].jar` file to anything you want (or not rename it), for the rest of this tutorial, we are going to rename it to `server.jar`.
3. (Optional) To let outside people that aren't in your network (WAN) join your ChattServer, port forward your IP address (on Windows you can find it out using `ipconfig`) with port `5000`. If you don't know how to do this, you may skip this step or search up how to port forward on your router (because steps may vary between router to router).
4. Run `java -jar server.jar` in the directory of where the `server.jar` is.
   - Or you can make a script that does this automatically so that you don't have to open the commandline every time you want to start the server. It varies between operating systems, though.
5. Tell your friends to join using your **public** IP address (not the one you entered in step 3); you may find it by searching up "what is my IP" on a search engine.
   - You do not need to tell them the port; it is the same for every ChattServer so the client will automatically use port 5000.

## How to build
You have my permission to fork my product, as long as it isn't used to invade privacy, isn't open source and isn't malware.\
This program uses Maven
; use it to build the project using Maven install.