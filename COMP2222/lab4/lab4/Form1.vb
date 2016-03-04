Public Class Form1
    Private Sub ToolStripButton1_Click(sender As Object, e As EventArgs) Handles ToolStripButton1.Click
        WebBrowser1.GoBack()
    End Sub

    Private Sub ToolStripButton2_Click(sender As Object, e As EventArgs) Handles ToolStripButton2.Click
        WebBrowser1.GoForward()
    End Sub

    Private Sub ToolStripButton3_Click(sender As Object, e As EventArgs) Handles ToolStripButton3.Click
        WebBrowser1.Navigate(ToolStripTextBox1.Text)

    End Sub

    Private Sub OpenToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles OpenToolStripMenuItem.Click
        digOpenFile.Filter = "HTML files (*.html;*.htm)|*.html;*.htm"
        digOpenFile.FileName = ""
        digOpenFile.ShowDialog()
        WebBrowser1.Navigate(digOpenFile.FileName)
    End Sub

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        ToolStripTextBox1.Text = "www.comp.polyu.edu.hk"
        WebBrowser1.Navigate(ToolStripTextBox1.Text)
    End Sub

    Private Sub SaveToolStripMenuItem1_Click(sender As Object, e As EventArgs) Handles SaveToolStripMenuItem1.Click
        WebBrowser1.ShowSaveAsDialog()
    End Sub
End Class
