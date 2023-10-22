#!/usr/bin/bash

count=0;
threshold=$(expr $(date +%s000) - 900000);

while read -r line;
do

	value=$(echo "$line" | cut -f 1 -d ' ' | tr -d '[]');
	[ $value -lt $threshold ] && { count=$(expr $count + 1); };

done < "$1";

[ $count -gt 0 ] && { 
	sed -i "1,${count}d" "$1";
};
