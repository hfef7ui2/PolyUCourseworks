####################
# The data segment #
####################

                 .data

#pre-stored information
strPromptEnter:    .asciiz "\nEnter a integer number(0-2,147,483,647):\n\n\n"
strPromptContinue: .asciiz "\n\n\nContinue (Input Y/y to continue)?\n\n\n"
strPromptReEnter:  .asciiz "Make sure your input is within the range: 0 - 2,147,483,647"
strBye:            .asciiz "\nBye"
strOverFlow:	   .asciiz "\n\n-------------!!!!!ATTENTION!!!!!-------------\nYour INPUT is equal to or larger than 2,147,483,647. \nWe can not judge whether your input exceed the upper bound.\nIf your INPUT is 2,147,483,647, please input 1. Otherwise, input any other character and input another number.\n-------------!!!!!ATTENTION!!!!!-------------\n\n\n"
#pre-stored decomposed character numbers
str0:            .asciiz " "
str1:            .asciiz "_"
str2:            .asciiz "|"
strNull:         .asciiz ""
strENTER:        .asciiz "\n"
strStart:           .asciiz "Input anything to start"
#pre-stored numbers
num0:              .word   0
num1:              .word   1
num2:              .word   2
num3:              .word   3
num4:              .word   4
num5:              .word   5
num6:              .word   6
num7:              .word   7
num8:              .word   8
num9:              .word   9
num10:		       .word   10
sentinel:		   .word   10
#pre-stored commands
cha1:              .byte 'Y'
cha2:              .byte 'y'
#space for storing character strings
array1:			   .space 1024
array2:			   .space 1024
array3:			   .space 1024

#####################
# The text segment  #
#####################

    .text
    .globl main

main:
    li $v0, 4 
    la $a0, strStart
    syscall     #print "Welcome"

    li $v0, 8                  
    syscall     #clear buffer


M0:
#capture user input
    jal readInt
    nop

#make a copy 0f user input
    move $a0, $v1

#break input into single digits and strore them in stack
    jal breakInt
	nop

#convert each single digits and strore them in memory
	jal convertInt
	nop

#print the charachater string
	jal printStr
	nop

#continue or not?
EXIT: 
    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    li $v0, 4                  
    la $a0, strPromptContinue   
    syscall	# print "Continue or not?"

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

  	li $v0, 12                  
    syscall                
    move $t0, $v0 	#capture inser input character

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line
	
	lb $t1, cha1
	beq $t1, $t0, M0
    lb $t1, cha2
    beq $t1, $t0, M0 #if user input is 'Y' or 'y', jump to beginning of the programme

#Bye and quit	
QUIT:	
    li $v0, 4                  
    la $a0, strBye   
    syscall		#print "Bye"

	li $v0, 10                 
	syscall     #Close programme   

##################
# End of Main    #
##################

#readInt -- read and store user input
#
#on entry:
#	$ra -- return address
#
#on return
#   
#print command"Enter ANY number"
	.text
	.globl breakInt
readInt:
RE: la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    li $v0, 4 
    la $a0, strPromptEnter     
    syscall       #print command"Enter ANY number\n"

    li $v0, 5                  
    syscall                   
    move $v1, $v0 #capture user input

    li $t0, 0
    slt $t1, $v1, $t0

    li $t2, 1
    bne $t2, $t1, CON

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    li $v0, 4 
    la $a0, strPromptReEnter     
    syscall       #print command"ReEnter\n"

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    j RE

CON:	li $t0, 2147483647
 	bne $v1, $t0, OUT

    la $a0, strOverFlow
    li $v0, 4 
    syscall       #print Overflow

    li $v0, 5                  
    syscall                   

    li $t0, 1
    beq $t0, $v0, OUT
    j RE

OUT:    jr $ra
    nop
    
#breakInt -- break number into seperated digits and push into stack
#
#on entry:
#	$ra -- return address
#	$a0 -- the integer to be breaked
#	$sp -- point to the top of the stack which contains the breaked digits

breakInt:
#pop 10 into stack as sentinel
#as digit is always non-negtive number, if we pop -1 from the stack, it means all the 
#digit have been poped out
    sub $sp, $sp, 4
    lw $t1, num10
    sw $t1, 0($sp)

#use 10 to dive input
	li $t0, 10
D0:	
    div $a0, $t0

	sub $sp, $sp, 4
	mfhi $t1 #store the mod in stack
	sw   $t1, 0($sp)
	mflo $t1 #store the quotient for next routine
	move $a0, $t1

	bne $a0, $zero, D0 #if the quotient is 0, all digit should have been stored
	jr $ra
	nop

#convertInt -- transform each digit into charactoer strings and strore into memory	
#
#on entry:
#	$ra -- return address
#   #$sp -- point to the top of stack which contains the breaked digits of input
convertInt:	
    lw $t4, sentinel  #load the sentinel value to detect the end of stack
	la $t5, array1
	la $t6, array2
	la $t7, array3    #load address of three array for storing character strings

NEXT:    
    lw $t0, 0($sp) #load next digit from stack
    add $sp, $sp, 4
	bne $t4, $t0, CONTINUE #if all digit have been poped, jump out of subroutine

	lb $t1, strNull
	sb $t1, 0($t5)
	lb $t1, strNull
	sb $t1, 0($t6)
	lb $t1, strNull
	sb $t1, 0($t7) #insert null-terminator to the each of each array

	j $ra
	nop

#move stack pointer to next word
CONTINUE:	
#use $t2 to travel the array
	li $t2, 4
	
# input examinsaiton: compare the input number with pre-stored number and jump to corresponding instructions
    lw $t1, num0
	beq $t1, $t0, L0	#compare user input with 0
	lw $t1, num1
	beq $t1, $t0, L1	#compare user input with 1
	lw $t1, num2
	beq $t1, $t0, L2	#compare user input with 2
	lw $t1, num3
	beq $t1, $t0, L3	#compare user input with 3
	lw $t1, num4
	beq $t1, $t0, L4	#compare user input with 4
	lw $t1, num5
	beq $t1, $t0, L5	#compare user input with 5
	lw $t1, num6
	beq $t1, $t0, L6	#compare user input with 6
	lw $t1, num7
	beq $t1, $t0, L7	#compare user input with 7
	lw $t1, num8
	beq $t1, $t0, L8	#compare user input with 8
	lw $t1, num9
	beq $t1, $t0, L9	#compare user input with 9	

#store the character strings into arrays
#if input is 0
L0: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str0
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t6, $t6, $t2

	lb $t1, str2
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit
	
#if input is 1
L1: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str0
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str0
	sb $t1, 0($t6)
	lb $t1, str0
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str0
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 2
L2: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str0
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str2
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str0
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 3	
L3: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str0
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 4	
L4: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str0
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str0
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 5	
L5: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str0
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 6	
L6: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str0
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str0
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str2
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 7	
L7: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str0
	sb $t1, 0($t6)
	lb $t1, str0
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str0
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 8	
L8: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str2
	sb $t1, 0($t7)
	lb $t1, str1
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2

	j NEXT	#end of branch and jump to print next digit

#if input is 9	
L9: 
    lb $t1, str0
	sb $t1, 0($t5)
	lb $t1, str1
	sb $t1, 1($t5)
	lb $t1, str0
	sb $t1, 2($t5)
	lb $t1, str0
	sb $t1, 3($t5)
	add $t5, $t5, $t2

    lb $t1, str2
	sb $t1, 0($t6)
	lb $t1, str1
	sb $t1, 1($t6)
	lb $t1, str2
	sb $t1, 2($t6)
	lb $t1, str0
	sb $t1, 3($t6)
	add $t6, $t6, $t2

	lb $t1, str0
	sb $t1, 0($t7)
	lb $t1, str0
	sb $t1, 1($t7)
	lb $t1, str2
	sb $t1, 2($t7)
	lb $t1, str0
	sb $t1, 3($t7)
	add $t7, $t7, $t2
	j NEXT	#end of branch and jump to print next digit

#printStr -- print the character strings
#
#	$ra -- return address

printStr:
    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    la $a0, array1
    li $v0, 4 
    syscall       #print top part of the character string

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    la $a0, array2
    li $v0, 4 
    syscall       #print middle part of the character string

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    la $a0, array3
    li $v0, 4 
    syscall       #print botton part of the character string

    la $a0, strENTER
    li $v0, 4 
    syscall       #next line

    jr $ra
    nop