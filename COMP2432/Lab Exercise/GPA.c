#include <stdio.h>

int main(int argc, char *argv[]) {
	int	num_subj;
	float in_gp = 0.0;
	float sum_gp = 0.0;
	char in_grade, grade[10];
	int	i;
	int j;
	int isPolyU = 1;
	int inValid = 0;

	/* argv[0] is the name of the program */
	num_subj = argc - 1;
	printf("PolyU:\n");
	for (j = 0; j < 2; j++) {
		for (i = 1; i <= num_subj; i++) {
			in_grade = argv[i][0]; /* read first character */
			switch (in_grade) {
			case 'A': in_gp = 4.0; break;
			case 'B': in_gp = 3.0; break;
			case 'C': in_gp = 2.0; break;
			case 'D': in_gp = 1.0; break;
			case 'F': in_gp = 0.0; break;
			default: printf("Wrong grade %s\n", argv[i]);
			}
			if (isPolyU == 1) {
				if (argv[i][1] == '+') in_gp = in_gp + 0.5;
				else if (argv[i][1] == '-') {
					printf("Grade for subject %d is %s, invalid\n", i, argv[i]);
					inValid++;
					continue;
				}
			}
			else {
				if (argv[i][1] == '-') {
					in_gp = in_gp - 0.3;
				}
				else if (argv[i][1] == '+') {
					if (in_gp == 4.0) {
						;
					}
					else if (in_gp == 1.0) {
						printf("Grade for subject %d is %s, invalid\n", i, argv[i]);
						inValid++;
						continue;
					}
					else {
						in_gp = in_gp + 0.3;
					}
				}
			}
			printf("Grade for subject %d is %s, GP %3.1f\n", i, argv[i], in_gp);
			sum_gp = sum_gp + in_gp;
		}
		printf("Your GPA for %d subjects is %4.2f\n", num_subj - inValid, sum_gp / (num_subj - inValid));
		if (isPolyU == 1) {
			printf("Other U:\n");
			sum_gp = 0.0;
			isPolyU = 0;
			inValid = 0;
		}
	}
}
