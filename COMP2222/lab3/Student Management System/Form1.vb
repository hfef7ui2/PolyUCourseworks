Public Class Form1
    Dim count As Integer = 0
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Dim name As String
        name = InputBox("Please Enter a Student Name: ", "Student Name", "Enter a student name here", 0, 0)
        If count = 5 Then
            MessageBox.Show("You can add at most 5 student")
        ElseIf name <> "" Then
            ComboBox1.Items.Add(name)
            count += 1
        End If
    End Sub

    Private Sub ComboBox1_SelectedIndexChanged(sender As Object, e As EventArgs) Handles ComboBox1.SelectedIndexChanged

    End Sub

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        ComboBox1.Items.Remove(ComboBox1.SelectedItem)
        ComboBox1.Text = ""
        count -= 1
    End Sub
End Class
