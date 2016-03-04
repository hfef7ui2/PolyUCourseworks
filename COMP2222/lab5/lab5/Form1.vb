Public Class Form1
    Private Sub Label1_Click(sender As Object, e As EventArgs) Handles Label1.Click

    End Sub

    Private Sub PictureBox1_Click(sender As Object, e As EventArgs) Handles PictureBox1.Click

    End Sub

    Private Sub ComboBox1_SelectedIndexChanged(sender As Object, e As EventArgs) Handles ComboBox1.SelectedIndexChanged
        If (ComboBox1.SelectedIndex() = 0) Then
            PictureBox1.Image = Image.FromFile("D:\Code\GitHub\PolyUCoursework\COMP2222\lab5\Lab_05_images\Task\cityu.jpg")
        ElseIf (ComboBox1.SelectedIndex() = 1) Then
            PictureBox1.Image = Image.FromFile("D:\Code\GitHub\PolyUCoursework\COMP2222\lab5\Lab_05_images\Task\cuhk.jpg")
        ElseIf (ComboBox1.SelectedIndex() = 2) Then
            PictureBox1.Image = Image.FromFile("D:\Code\GitHub\PolyUCoursework\COMP2222\lab5\Lab_05_images\Task\hku.jpg")
        ElseIf (ComboBox1.SelectedIndex() = 3) Then
            PictureBox1.Image = Image.FromFile("D:\Code\GitHub\PolyUCoursework\COMP2222\lab5\Lab_05_images\Task\polyu.jpg")
        ElseIf (ComboBox1.SelectedIndex() = 4) Then
            PictureBox1.Image = Image.FromFile("D:\Code\GitHub\PolyUCoursework\COMP2222\lab5\Lab_05_images\Task\ust.jpg")
        End If
    End Sub

    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        ComboBox1.SelectedIndex = CInt(Math.Floor((4 - 0 + 1) * Rnd())) + 0
    End Sub
End Class
