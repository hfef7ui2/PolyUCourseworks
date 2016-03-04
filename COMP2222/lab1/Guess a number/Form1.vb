Public Class Form1
    Dim R As New Random(Now.Millisecond)
    Dim resultNo As Integer = R.Next(1, 100)

    '    Private Sub Form1_Load(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Me.Load
    '        inputNumber.Text = resultNo
    '    End Sub

    Private Sub Button_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button.Click
        Dim input As Integer
        input = inputNumber.Text
        If (input < 1 Or input > 100) Then
            MessageBox.Show("Please enter a number between 1 and 100")
        ElseIf (input < resultNo) Then
            outputListBox.Items.Add("The answer is greater than " & input)
        ElseIf (input > resultNo) Then
            outputListBox.Items.Add("The answer is smaller than " & input)
        Else
            outputListBox.Items.Add("Correct")
            Me.BackColor = Color.Red
        End If
    End Sub

End Class
