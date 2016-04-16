Imports Scripting

Public Class Form1

    Dim Src, Dst As String
    Dim file As FileSystemObject = New FileSystemObject
    Dim SrcSize As Double

    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        OpenFileDialog1.InitialDirectory = "C:\"
        OpenFileDialog1.Filter = "Text Document (*.txt)|*.txt|All Files (*.*)|*.*"
        OpenFileDialog1.Title = "Open File Dialog"
        If OpenFileDialog1.ShowDialog() = System.Windows.Forms.DialogResult.OK Then
            MsgBox(OpenFileDialog1.FileName)
            Src = OpenFileDialog1.FileName
            TextBox1.Text = Src
        End If

        ResetThings()
        Timer1.Stop()
    End Sub

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        With SaveFileDialog1
            .DefaultExt = "txt"
            .FileName = Src
            .Filter = "Text Document (*.txt)|*.txt|All Files (*.*)|*.*"
            .Title = "Save File Dialog"
        End With

        If SaveFileDialog1.ShowDialog() = System.Windows.Forms.DialogResult.OK Then
            Dst = SaveFileDialog1.FileName
            MsgBox(SaveFileDialog1.FileName)
            TextBox2.Text = Dst
        End If

    End Sub


    Private Sub Button3_Click(sender As Object, e As EventArgs) Handles Button3.Click
        FileCopy(Src, Dst)
        Timer1.Start()
    End Sub

    Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
        Dim s, d As System.IO.FileInfo
        s = My.Computer.FileSystem.GetFileInfo(Src)
        d = My.Computer.FileSystem.GetFileInfo(Dst)

    Dim Percent As Integer = d.Length / s.Length * 100
    If d.Length = -1 Then
            ResetThings()
        Else
            Label1.Text = Percent & "% Percent"
            ProgressBar1.Value = Percent

        End If
    End Sub

    Private Sub ResetThings()
        ProgressBar1.Value = 0
        Label1.Text = ""
    End Sub

End Class
