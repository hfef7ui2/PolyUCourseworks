Public Class Form1
    Dim sum As Double
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        sum = 0
        Dim name As String
        name = TextBox1.Text
        If (name = "") Then
            MessageBox.Show("Error! Please enter your name!")
            Return
        End If
        If (RadioButton1.Checked = False And RadioButton2.Checked = False) Then
            MessageBox.Show("Error! Please select one main course at least!")
            Return
        End If
        If (CheckBox1.Checked = True) Then
            sum += 35
        End If
        If (CheckBox2.Checked = True) Then
            sum += 30
        End If
        If (CheckBox3.Checked = True) Then
            sum += 32
        End If
        If (RadioButton1.Checked = True) Then
            sum += 30
        Else
            sum += 32
        End If
        Select Case ComboBox1.SelectedIndex
            Case 0
                sum += 25
            Case 1
                sum += 28
        End Select
        sum *= 1.1
        Label4.Text = sum
        sum = 0
    End Sub
End Class
