####################
# The data segment #
####################

                 .data

#pre-stored information
strPromptEnter:    .asciiz "\nEnter a single digit (0-9):"
strPromptContinue: .asciiz "Continue (Y/N)?"
strBye:            .asciiz "\nBye"
#pre-stored decomposed character numbers
str000:            .asciiz "   \n"
str010:            .asciiz " _ \n"
str002:            .asciiz "  |\n"
str012:            .asciiz " _|\n"
str212:            .asciiz "|_|\n"
str210:            .asciiz "|_ \n"
str202:            .asciiz "| |\n"
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
#pre-stored commands
cha1:              .byte 'Y'
cha2:              .byte 'N'

#####################
# The text segment  #
#####################

    .text
    .globl main

main:

#print command"Enter a single digit"
M0: li $v0, 4                  
    la $a0, strPromptEnter     
    syscall       #print command"Enter a single digit"

    li $v0, 5                  
    syscall                   
    move $t0, $v0 #capture user input    
	
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
	
	j EXIT

#if input is 0
L0: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 0
	
	la $a0, str202
	li $v0, 4
	syscall		#print middle part of character number 0
	
	la $a0, str212
	li $v0, 4
	syscall		#print botton part of character number 0
	
	j EXIT	#end of branch
	
#if input is 1
L1: la $a0, str000
	li $v0, 4
	syscall		#print top part of character number 1
	
	la $a0, str002
	li $v0, 4
	syscall		#print middle part of character number 1
	
	la $a0, str002
	li $v0, 4
	syscall		#print botton part of character number 1
	
	j EXIT	#end of branch

#if input is 2
L2: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 2
	
	la $a0, str012
	li $v0, 4
	syscall		#print middle part of character number 2
	
	la $a0, str210
	li $v0, 4
	syscall		#print botton part of character number 2
	
	j EXIT	#end of branch

#if input is 3	
L3: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 3
	
	la $a0, str012
	li $v0, 4
	syscall		#print middle part of character number 3
	
	la $a0, str012
	li $v0, 4
	syscall		#print botton part of character number 3
	
	j EXIT	#end of branch

#if input is 4	
L4: la $a0, str000
	li $v0, 4
	syscall		#print top part of character number 4
	
	la $a0, str212
	li $v0, 4
	syscall		#print middle part of character number 4
	
	la $a0, str002
	li $v0, 4
	syscall		#print botton part of character number 4
	
	j EXIT	#end of branch

#if input is 5	
L5: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 5
	
	la $a0, str210
	li $v0, 4
	syscall		#print middle part of character number 5
	
	la $a0, str012
	li $v0, 4
	syscall		#print botton part of character number 5
	
	j EXIT	#end of branch

#if input is 6	
L6: la $a0, str000
	li $v0, 4
	syscall		#print top part of character number 6
	
	la $a0, str210
	li $v0, 4
	syscall		#print middle part of character number 6
	
	la $a0, str212
	li $v0, 4
	syscall		#print botton part of character number 6
	
	j EXIT	#end of branch

#if input is 7	
L7: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 7
	
	la $a0, str002
	li $v0, 4
	syscall		#print middle part of character number 7
	
	la $a0, str002
	li $v0, 4
	syscall		#print botton part of character number 7
	
	j EXIT	#end of branch

#if input is 8	
L8: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 8
	
	la $a0, str212
	li $v0, 4
	syscall		#print middle part of character number 8
	
	la $a0, str212
	li $v0, 4
	syscall		#print botton part of character number 8
	
	j EXIT	#end of branch

#if input is 9	
L9: la $a0, str010
	li $v0, 4
	syscall		#print top part of character number 9
	
	la $a0, str212
	li $v0, 4
	syscall		#print middle part of character number 9
	
	la $a0, str002
	li $v0, 4
	syscall		#print botton part of character number 9
	
	j EXIT	#end of branch

#continue or not?
EXIT: li $v0, 4                  
      la $a0, strPromptContinue   
      syscall	# print "Continue or not?"
	  
	li $v0, 12                  
    syscall                
    move $t0, $v0 	#capture inser input character
	
	lb $t1, cha1
	bne $t1, $t0, QUIT
	j M0	#if user input is 'Y', jump to beginning of the programme

#Bye and quit	
QUIT:	li $v0, 4                  
    la $a0, strBye   
    syscall		#print "Bye"

	li $v0, 10                 
	syscall     #Close programme   

##################
# End of Program #
##################
