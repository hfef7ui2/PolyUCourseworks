Public Class Form1
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Form3.Hide()
        Form2.Show()
    End Sub

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        Form3.Show()
        Form2.Hide()
    End Sub

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Form2.TopLevel = False
        Form3.TopLevel = False
        Me.Panel1.Controls.Add(Form2)
        Me.Panel1.Controls.Add(Form3)
    End Sub
End Class
