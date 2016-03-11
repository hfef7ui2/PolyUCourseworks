#!/bin/bash

cards=(N/A SA HA DA CA SK HK DK CK SQ HQ DQ CQ SJ HJ DJ CJ ST HT DT CT S9 G9 D9 C9 S8 H8 D8 C8 S7 H7 D7 C7 S6 H6 D6 C6 S5 H5 D5 C5 S4 H4 D4 C4 S3 H3 D3 C3 S2 H2 D2 C2)
record=(N/A 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)
	    
echo -e "The hand is\c"
declare -i numOfCards=0
declare -i next=0
if [ $# -eq 0 ]; then
    for ((i=1; i<=13; i++)); do
        next=$(( $RANDOM % 52 + 1 ))
	until [ ${record[next]}  -eq 0 ]; do
	    next=$(( $RANDOM % 52 + 1 ))
        done
	record[$next]=1 
        echo -e " ${cards[$next]}\c"
     done
    numOfCards=13
else	    
    for input in $*; do
        if [ ${record[$input]} = 0 ]; then
            echo -e " ${cards[$input]}\c"
            record[$input]=1
	    let numOfCards++
        else
            echo -e " (!!!DUPLICATE: ${cards[$input]} is ABORTED!!!)\c"
        fi
    done
fi

echo -e "\nSorted hand\c"
for ((i=1; i<=52; i++)); do
    if [ ${record[i]} -eq 1 ]; then
        echo -e " ${cards[i]}\c"
    fi
done

declare -i sum=0
for ((j=1; j<=52; j++)); do
    sum=$(( sum | (  ${record[j]} << ( ( j - 1 ) % 4 ) ) ))
    if [ `expr $j % 4` -eq 0 ]; then
	if [[ $sum =~ 9|10|11|12|13|15 ]]; then
	    record[j]=0
	fi
        if [[ $sum =~ 5|6|7|12|14|15 ]]; then
            record[j-1]=0
        fi
        if [[ $sum =~ 3|6|7|10|14|15 ]]; then
            record[j-2]=0
        fi
        if [[ $sum =~ 3|5|9|11|13|15 ]]; then
            record[j-3]=0
        fi
	sum=0
    fi
done

declare -i leftCards=0
echo -e "\nRemainning cards\c"
for ((i=1; i<=52; i++)); do
    if [ ${record[i]} -eq 1 ]; then
        echo -e " ${cards[i]}\c"
	let leftCards++
    fi
done

echo -e "\nNumber of pairs eliminated $(( ( $numOfCards - $leftCards ) / 2 ))\nNumber of remaining cards $leftCards"
