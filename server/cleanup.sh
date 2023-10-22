#!/usr/bin/bash

path=$(realpath .)
groups="$path/groups";

for file in $(ls $groups)
do
	$path/remove_old_chats.sh "$groups/$file"
done;
